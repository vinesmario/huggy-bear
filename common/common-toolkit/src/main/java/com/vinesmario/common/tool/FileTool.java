package com.vinesmario.common.tool;

import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.bzip2.BZip2Utils;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.apache.commons.compress.compressors.xz.XZUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileTool {

    private static final String SIZE_MEASUREMENT_UNIT_B = "B";
    private static final String SIZE_MEASUREMENT_UNIT_K = "K";
    private static final String SIZE_MEASUREMENT_UNIT_M = "M";
    private static final String SIZE_MEASUREMENT_UNIT_G = "G";
    private static final String SIZE_MEASUREMENT_UNIT_T = "T";

    private static final long SIZE_RADIX_B = 1;
    private static final long SIZE_RADIX_K = SIZE_RADIX_B * 1024;
    private static final long SIZE_RADIX_M = SIZE_RADIX_K * 1024;
    private static final long SIZE_RADIX_G = SIZE_RADIX_M * 1024;
    private static final long SIZE_RADIX_T = SIZE_RADIX_G * 1024;

    /*=========压缩文件 start=========*/

    /**
     * 压缩文件（夹）
     *
     * @param srcDirAbsolutePath  源文件夹绝对路径
     * @param srcFileName         源文件名称
     * @param destDirAbsolutePath 目标文件夹绝对路径
     * @param destFileName        目标文件名称
     * @param compressType        压缩类型：ar,arj,cpio,dump,jar,tar,zip;bzip2,gz,pack200,xz,lzma
     * @throws Exception
     */
    public void compress(String srcDirAbsolutePath, String srcFileName,
                         String destDirAbsolutePath, String destFileName, String compressType) throws Exception {
        //ar,arj,cpio,dump,jar,tar,zip
        if (CompressorFactory.archiversMap.containsKey(compressType.toLowerCase())) {
            compressArchiver(srcDirAbsolutePath, srcFileName, destDirAbsolutePath, destFileName, compressType);
        }
        //bzip2,gz,pack200,xz,lzma
        else if (CompressorFactory.compressorsMap.containsKey(compressType.toLowerCase())) {
            compressCompressor(srcDirAbsolutePath, srcFileName, destDirAbsolutePath, destFileName, compressType);
        }
        //7z
        else if (CompressorFactory.othersMap.containsKey(compressType.toLowerCase())) {
            throw new IllegalArgumentException("Compressor or archiver " + compressType
                    + " not support.");
        } else {
            throw new IllegalArgumentException("Compressor or archiver " + compressType
                    + " not found.");
        }
    }

    /**
     * 压缩文件（夹）
     *
     * @param srcDirAbsolutePath  源文件夹绝对路径
     * @param srcFileName         源文件名称
     * @param destDirAbsolutePath 目标文件夹绝对路径
     * @param destFileName        目标文件名称
     * @param archiverName        压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void compressArchiver(String srcDirAbsolutePath, String srcFileName,
                                 String destDirAbsolutePath, String destFileName, String archiverName)
            throws IOException, ArchiveException {
        if (StringUtils.isBlank(srcDirAbsolutePath)) {
            throw new IllegalArgumentException("Source Directory's absolute path must not be null.");
        } else if (StringUtils.isBlank(destDirAbsolutePath)) {
            throw new IllegalArgumentException("Destination Directory's absolute path must not be null.");
        }

        String srcFileAbsolutePath;
        if (StringUtils.isBlank(srcFileName)) {
            srcFileAbsolutePath = srcDirAbsolutePath;
        } else {
            srcFileAbsolutePath = StringUtils.join(srcDirAbsolutePath, "/", srcFileName);
        }

        String destFileAbsolutePath;
        String archiverExtensionName = CompressorFactory.archiversMap.get(archiverName);
        if (StringUtils.isBlank(destFileName)) {
            destFileAbsolutePath = StringUtils.join(destDirAbsolutePath, "/",
                    FilenameUtils.getBaseName(srcFileAbsolutePath), ".", archiverExtensionName);
        } else {
            //校验文件名是否与压缩格式冲突
            String destFileExtensionName = FilenameUtils.getExtension(destFileName);
            if (!destFileExtensionName.equals(archiverExtensionName)) {
                throw new IllegalArgumentException("Archiver file's extenstion name is " + archiverExtensionName
                        + " but destination file's extenstion name is " + destFileExtensionName);
            }
            destFileAbsolutePath = StringUtils.join(destDirAbsolutePath, "/", destFileName);
        }
        compressArchiver(srcFileAbsolutePath, destFileAbsolutePath, archiverName);
    }

    /**
     * 压缩多个文件(夹)
     *
     * @param srcFileList          源文件(夹)列表
     * @param destFileAbsolutePath 目标文件绝对路径
     * @param archiverName         压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void compressArchiverList(List<File> srcFileList, String destFileAbsolutePath, String archiverName)
            throws IOException, ArchiveException {
        File destFile = new File(destFileAbsolutePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        compressArchiverList(srcFileList, destFile, archiverName);
    }

    /**
     * 压缩多个文件(夹)
     *
     * @param srcFileList  源文件(夹)列表
     * @param destFile     目标文件
     * @param archiverName 压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void compressArchiverList(List<File> srcFileList, File destFile, String archiverName)
            throws IOException, ArchiveException {
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        FileOutputStream fos = FileUtils.openOutputStream(destFile);
        compressArchiverList(srcFileList, fos, archiverName);
        fos.close();
    }

    /**
     * 压缩多个文件(夹)
     *
     * @param srcFileList  源文件(夹)列表
     * @param os           目标流
     * @param archiverName 压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void compressArchiverList(List<File> srcFileList, OutputStream os, String archiverName)
            throws IOException, ArchiveException {
        ArchiveOutputStream aos = new ArchiveStreamFactory().createArchiveOutputStream(archiverName.toLowerCase(), os);
        for (File srcFile : srcFileList) {
            appendFileToArchive(srcFile, aos);
        }
        aos.finish();
        aos.close();
    }

    /**
     * 压缩文件(夹)
     *
     * @param srcAbsolutePath      源文件(夹)绝对路径
     * @param destFileAbsolutePath 目标文件绝对路径
     * @param archiverName         压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void compressArchiver(String srcAbsolutePath, String destFileAbsolutePath, String archiverName)
            throws IOException, ArchiveException {
        if (StringUtils.isBlank(srcAbsolutePath)) {
            throw new IllegalArgumentException("Source's absolute path must not be null.");
        }
        compressArchiver(new File(srcAbsolutePath), destFileAbsolutePath, archiverName);
    }

    /**
     * 压缩文件(夹)
     *
     * @param srcFile              源文件(夹)
     * @param destFileAbsolutePath 目标文件绝对路径
     * @param archiverName         压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void compressArchiver(File srcFile, String destFileAbsolutePath, String archiverName)
            throws IOException, ArchiveException {
        if (StringUtils.isBlank(destFileAbsolutePath)) {
            throw new IllegalArgumentException("Destination's absolute path must not be null.");
        }
        compressArchiver(srcFile, new File(destFileAbsolutePath), archiverName);
    }

    /**
     * 压缩文件(夹)
     *
     * @param srcFile      源文件(夹)
     * @param destFile     目标文件
     * @param archiverName 压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void compressArchiver(File srcFile, File destFile, String archiverName)
            throws IOException, ArchiveException {
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        FileOutputStream fos = FileUtils.openOutputStream(destFile);
        compressArchiver(srcFile, fos, archiverName);
        fos.close();
    }

    /**
     * 压缩文件(夹)
     *
     * @param srcFile      源文件(夹)
     * @param os           输出流
     * @param archiverName 压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void compressArchiver(File srcFile, OutputStream os, String archiverName)
            throws IOException, ArchiveException {
        ArchiveOutputStream aos = new ArchiveStreamFactory().createArchiveOutputStream(archiverName.toLowerCase(), os);
        appendFileToArchive(srcFile, aos);
        aos.finish();
        aos.close();
    }

    /**
     * 递归向压缩文件流中添加文件（夹）
     *
     * @param aos     压缩文件流
     * @param srcFile 源文件(夹)
     * @throws IOException
     */
    private void appendFileToArchive(File srcFile, ArchiveOutputStream aos)
            throws IOException {
        ArchiveEntry entry = aos.createArchiveEntry(srcFile, srcFile.getAbsolutePath());
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (srcFile.isDirectory()) {
            File[] children = srcFile.listFiles();
            for (int i = 0; i < children.length; i++) {
                appendFileToArchive(children[i], aos);
            }
            if (entry.getSize() > 0) {
                aos.putArchiveEntry(entry);
                aos.closeArchiveEntry();
            }
        } else if (srcFile.isFile()) {
            aos.putArchiveEntry(entry);
            FileInputStream fis = FileUtils.openInputStream(srcFile);
            IOUtils.copy(fis, aos);
            aos.closeArchiveEntry();
            fis.close();
        }
    }

    /**
     * 压缩文件
     *
     * @param srcDirAbsolutePath  源文件夹绝对路径
     * @param srcFileName         源文件名称
     * @param destDirAbsolutePath 目标文件夹绝对路径
     * @param destFileName        目标文件名称
     * @param compressName        压缩类型:bzip2,gz,pack200,xz,lzma
     * @throws IOException
     * @throws CompressorException
     */
    public void compressCompressor(String srcDirAbsolutePath, String srcFileName,
                                   String destDirAbsolutePath, String destFileName, String compressName)
            throws IOException, CompressorException {
        if (StringUtils.isBlank(srcDirAbsolutePath)) {
            throw new IllegalArgumentException("Source Directory's absolute path must not be null.");
        } else if (StringUtils.isBlank(destDirAbsolutePath)) {
            throw new IllegalArgumentException("Destination Directory's absolute path must not be null.");
        }

        if (StringUtils.isBlank(srcFileName)) {
            throw new IllegalArgumentException("Source File's name must not be null.");
        }
        String srcFileAbsolutePath = StringUtils.join(srcDirAbsolutePath, "/", srcFileName);

        String destFileAbsolutePath;
        String compressExtensionName = CompressorFactory.compressorsMap.get(compressName);
        if (StringUtils.isBlank(destFileName)) {
            destFileAbsolutePath = StringUtils.join(destDirAbsolutePath, "/",
                    FilenameUtils.getBaseName(srcFileAbsolutePath), ".", compressExtensionName);
        } else {
            //校验文件名是否与压缩格式冲突
            String destFileExtensionName = FilenameUtils.getExtension(destFileName);
            if (!destFileExtensionName.equals(compressExtensionName)) {
                throw new IllegalArgumentException("Compress File's extenstion name is " + compressExtensionName
                        + " but destination file's extenstion name is " + destFileExtensionName);
            }
            destFileAbsolutePath = StringUtils.join(destDirAbsolutePath, "/", destFileName);
        }
        compressCompressor(srcFileAbsolutePath, destFileAbsolutePath, compressName);
    }

    /**
     * 压缩文件
     *
     * @param srcFileAbsolutePath  源文件绝对路径
     * @param destFileAbsolutePath 目标文件绝对路径
     * @param compressName
     * @throws IOException
     * @throws CompressorException
     */
    public void compressCompressor(String srcFileAbsolutePath, String destFileAbsolutePath, String compressName)
            throws IOException, CompressorException {
        if (StringUtils.isBlank(srcFileAbsolutePath)) {
            throw new IllegalArgumentException("Source's absolute path must not be null.");
        }
        compressCompressor(new File(srcFileAbsolutePath), destFileAbsolutePath, compressName);
    }

    /**
     * 压缩文件
     *
     * @param srcFile              源文件
     * @param destFileAbsolutePath 目标文件绝对路径
     * @param compressName
     * @throws IOException
     * @throws CompressorException
     */
    public void compressCompressor(File srcFile, String destFileAbsolutePath, String compressName)
            throws IOException, CompressorException {
        if (StringUtils.isBlank(destFileAbsolutePath)) {
            throw new IllegalArgumentException("Destination's absolute path must not be null.");
        }
        compressCompressor(srcFile, new File(destFileAbsolutePath), compressName);
    }

    /**
     * 压缩文件
     *
     * @param srcFile      源文件
     * @param destFile     目标文件
     * @param compressName 压缩类型:bzip2,gz,pack200,xz,lzma
     * @throws IOException
     * @throws CompressorException
     */
    public void compressCompressor(File srcFile, File destFile, String compressName)
            throws IOException, CompressorException {
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        FileOutputStream fos = FileUtils.openOutputStream(destFile);
        compressCompressor(srcFile, fos, compressName);
        fos.close();
    }

    /**
     * 压缩文件
     *
     * @param srcFile      源文件
     * @param os           目标流
     * @param compressName 压缩类型:bzip2,gz,pack200,xz,lzma
     * @throws IOException
     * @throws CompressorException
     */
    public void compressCompressor(File srcFile, OutputStream os, String compressName)
            throws IOException, CompressorException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        FileInputStream fis = FileUtils.openInputStream(srcFile);
        CompressorOutputStream cos = new CompressorStreamFactory().createCompressorOutputStream(compressName.toLowerCase(), os);
        IOUtils.copy(fis, cos);
        cos.close();
        fis.close();
    }
    /*=========压缩文件 end=========*/

    /*=========解压文件 start=========*/

    /**
     * @param srcDirAbsolutePath  源文件夹绝对路径
     * @param srcFileName         源文件名称
     * @param destDirAbsolutePath 目标文件夹绝对路径
     * @param compressType        压缩类型：ar,arj,cpio,dump,jar,tar,zip;bzip2,gz,pack200,xz,lzma
     * @return String 解压后的文件全路径
     * @throws Exception
     */
    public String uncompress(String srcDirAbsolutePath, String srcFileName,
                             String destDirAbsolutePath, String compressType) throws Exception {
        String srcFileAbsolutePath = StringUtils.join(srcDirAbsolutePath, "/", srcFileName);
        return uncompress(srcFileAbsolutePath, destDirAbsolutePath, compressType);
    }

    /**
     * 解压文件
     *
     * @param srcFileAbsolutePath 源文件绝对路径
     * @param destDirAbsolutePath 目标文件夹绝对路径
     * @param compressType        压缩类型：ar,arj,cpio,dump,jar,tar,zip;bzip2,gz,pack200,xz,lzma
     * @return String 解压后的文件全路径
     * @throws Exception
     */
    public String uncompress(String srcFileAbsolutePath, String destDirAbsolutePath, String compressType)
            throws Exception {
        //ar,arj,cpio,dump,jar,tar,zip
        if (CompressorFactory.archiversMap.containsKey(compressType.toLowerCase())) {
            uncompressArchiver(srcFileAbsolutePath, destDirAbsolutePath, compressType);
            return null;
        }
        //bzip2,gz,pack200,xz,lzma
        else if (CompressorFactory.compressorsMap.containsKey(compressType.toLowerCase())) {
            return uncompressCompressor(srcFileAbsolutePath, destDirAbsolutePath, compressType);
        }
        //7z
        else if (CompressorFactory.othersMap.containsKey(compressType.toLowerCase())) {
            throw new IllegalArgumentException("Compressor or archiver "
                    + compressType + " not support.");
        } else {
            throw new IllegalArgumentException("Compressor or archiver "
                    + compressType + " not found.");
        }
    }

    /**
     * 解压文件
     *
     * @param srcFileAbsolutePath 源文件绝对路径
     * @param destDirAbsolutePath 目标文件夹绝对路径
     * @param archiverName        压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void uncompressArchiver(String srcFileAbsolutePath, String destDirAbsolutePath, String archiverName)
            throws IOException, ArchiveException {
        if (StringUtils.isBlank(srcFileAbsolutePath)) {
            throw new IllegalArgumentException("Source's absolute path must not be null.");
        }
        uncompressArchiver(new File(srcFileAbsolutePath), destDirAbsolutePath, archiverName);
    }

    /**
     * 解压文件
     *
     * @param srcFile             源文件
     * @param destDirAbsolutePath 目标文件夹绝对路径
     * @param archiverName        压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void uncompressArchiver(File srcFile, String destDirAbsolutePath, String archiverName)
            throws IOException, ArchiveException {
        if (StringUtils.isBlank(destDirAbsolutePath)) {
            throw new IllegalArgumentException("Destination's absolute path must not be null.");
        }
        uncompressArchiver(srcFile, new File(destDirAbsolutePath), archiverName);
    }

    /**
     * 解压文件
     *
     * @param srcFile      源文件
     * @param destDir      目标文件夹
     * @param archiverName 压缩类型：ar,arj,cpio,dump,jar,tar,zip
     * @throws IOException
     * @throws ArchiveException
     */
    public void uncompressArchiver(File srcFile, File destDir, String archiverName)
            throws IOException, ArchiveException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        FileInputStream fis = FileUtils.openInputStream(srcFile);
        ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream(archiverName.toLowerCase(), fis);

        ArchiveEntry entry = null;
        while (null != (entry = ais.getNextEntry())) {
            File entryFile = new File(StringUtils.join(destDir.getAbsolutePath(), "/", entry.getName()));
            if (!entry.isDirectory()) {
                FileOutputStream fos = FileUtils.openOutputStream(entryFile);
                if (ais.canReadEntryData(entry)) {
                    IOUtils.copy(ais, fos);
                }
                fos.close();
            }
        }
        ais.close();
        fis.close();
    }

    /**
     * 解压文件
     *
     * @param srcFileAbsolutePath 源文件绝对路径
     * @param destDirAbsolutePath 目标文件夹绝对路径
     * @param compressName        压缩类型:bzip2,gz,pack200,xz,lzma
     * @return destFileName 解压后的文件名
     * @throws IOException
     * @throws CompressorException
     */
    public String uncompressCompressor(String srcFileAbsolutePath, String destDirAbsolutePath, String compressName)
            throws IOException, CompressorException {
        if (StringUtils.isBlank(srcFileAbsolutePath)) {
            throw new IllegalArgumentException("Source's absolute path must not be null.");
        }
        String destFileName = null;
        if (CompressorStreamFactory.GZIP.equalsIgnoreCase(compressName.toLowerCase())) {
            destFileName = GzipUtils.getUncompressedFilename(srcFileAbsolutePath);
        } else if (CompressorStreamFactory.BZIP2.equalsIgnoreCase(compressName.toLowerCase())) {
//            org.apache.commons.compress.compressors.bzip2.BZip2Utils line35
            //uncompressSuffix should put <".bzip2","">
            String extension = FilenameUtils.getExtension(srcFileAbsolutePath).replace(CompressorStreamFactory.BZIP2, "bz2");
            String baseName = FilenameUtils.getBaseName(srcFileAbsolutePath);
            destFileName = BZip2Utils.getUncompressedFilename(baseName + "." + extension);
        } else if (CompressorStreamFactory.XZ.equalsIgnoreCase(compressName.toLowerCase())) {
            destFileName = XZUtils.getUncompressedFilename(srcFileAbsolutePath);
        } else if (CompressorStreamFactory.LZMA.equalsIgnoreCase(compressName.toLowerCase())) {
            //暂未实现
            throw new IllegalArgumentException("Compressor or archiver " + compressName + " not support.");
        } else if (CompressorStreamFactory.PACK200.equalsIgnoreCase(compressName.toLowerCase())) {
            //暂未实现
            throw new IllegalArgumentException("Compressor or archiver " + compressName + " not support.");
        }
        String destFileAbsolutePath = StringUtils.join(destDirAbsolutePath, "/", destFileName);
        uncompressCompressor(new File(srcFileAbsolutePath), destFileAbsolutePath, compressName);
        return destFileName;
    }

    /**
     * 解压文件
     *
     * @param srcFile              源文件
     * @param destFileAbsolutePath 目标文件绝对路径
     * @param compressName         压缩类型:bzip2,gz,pack200,xz,lzma
     * @throws Exception
     * @throws CompressorException
     */
    public void uncompressCompressor(File srcFile, String destFileAbsolutePath, String compressName)
            throws IOException, CompressorException {
        if (StringUtils.isBlank(destFileAbsolutePath)) {
            throw new IllegalArgumentException("Destination's absolute path must not be null.");
        }
        uncompressCompressor(srcFile, new File(destFileAbsolutePath), compressName);
    }

    /**
     * 解压文件
     *
     * @param srcFile      源文件
     * @param destFile     目标文件
     * @param compressName 压缩类型:bzip2,gz,pack200,xz,lzma
     * @throws Exception
     * @throws CompressorException
     */
    public void uncompressCompressor(File srcFile, File destFile, String compressName)
            throws IOException, CompressorException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        FileInputStream fis = FileUtils.openInputStream(srcFile);
        FileOutputStream fos = FileUtils.openOutputStream(destFile);
        CompressorInputStream cis = new CompressorStreamFactory().createCompressorInputStream(compressName.toLowerCase(), fis);
        IOUtils.copy(cis, fos);
        cis.close();
        fis.close();
        fos.close();
    }
    /*=========解压文件 end=========*/

    /*=========重命名文件start=========*/

    /**
     * 重命名文件
     *
     * @param srcFileAbsolutePath  原文件绝对路径
     * @param destFileAbsolutePath 新文件绝对路径
     */
    public void renameFile(String srcFileAbsolutePath, String destFileAbsolutePath) {
        if (StringUtils.isBlank(srcFileAbsolutePath)) {
            throw new IllegalArgumentException("Source's absolute path must not be null.");
        }
        if (StringUtils.isBlank(destFileAbsolutePath)) {
            throw new IllegalArgumentException("Destination's absolute path must not be null.");
        }
        renameFile(new File(srcFileAbsolutePath), new File(destFileAbsolutePath));
    }

    /**
     * 重命名文件
     *
     * @param srcFile  原文件名文件对象
     * @param destFile 新文件名文件对象
     */
    private void renameFile(File srcFile, File destFile) {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        srcFile.renameTo(destFile);
    }

    /*=========检查文件 start=========*/
    /**
     * 检查源文件(夹)与目标文件(夹)是否相同
     *
     * @param src  源文件(夹)
     * @param dest 目标文件(夹)
     * @throws IOException
     */
    public void checkSrcEqualDest(File src, File dest) throws IOException {
        if (src.getCanonicalPath().equals(dest.getCanonicalPath())) {
            throw new IOException("Source '" + src.getAbsolutePath() + "' and destination '"
                    + dest.getAbsolutePath() + "' are the same");
        }
    }
    /*=========检查文件 end=========*/

    /**
     * 获取文件名的最后一段
     * <pre>
     * 	C:/abc/sz/abc.sz 			-->abc.sz
     *  //server/d/abc.sz.tar.gz 	-->abc.sz.tar.gz
     *  abc							-->abc
     *  C:/abd/						-->""
     * </pre>
     *
     * @param filename
     * @return
     */
    public String getLastFilename(String filename) {
        if (null == filename) {
            return null;
        }
        if (filename.endsWith("/") || filename.endsWith("\\")) {
            return "";
        }
        int index = filename.lastIndexOf('/');
        if (-1 == index) {
            index = filename.lastIndexOf('\\');
        }
        return -1 == index ? filename : filename.substring(index + 1);
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     * @throws IOException
     */
    public String getFileSize(File file) throws IOException {
        double fileSize = file.length();
        String fileSizeString = "0.00B";
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileSize < SIZE_RADIX_K) {
            fileSizeString = df.format(fileSize) + SIZE_MEASUREMENT_UNIT_B;
        } else if (fileSize < SIZE_RADIX_M) {
            fileSizeString = df.format(fileSize / SIZE_RADIX_K) + SIZE_MEASUREMENT_UNIT_K;
        } else if (fileSize < SIZE_RADIX_G) {
            fileSizeString = df.format(fileSize / SIZE_RADIX_M) + SIZE_MEASUREMENT_UNIT_M;
        } else if (fileSize < SIZE_RADIX_T) {
            fileSizeString = df.format(fileSize / SIZE_RADIX_G) + SIZE_MEASUREMENT_UNIT_G;
        } else {
            fileSizeString = df.format(fileSize / SIZE_RADIX_T) + SIZE_MEASUREMENT_UNIT_T;
        }
        return fileSizeString;
    }

    public static class CompressorFactory {
        public static final Map<String, String> archiversMap = new HashMap<String, String>();
        public static final Map<String, String> compressorsMap = new HashMap<String, String>();
        public static final Map<String, String> othersMap = new HashMap<String, String>();
        public static final Map<String, String> compressorFactoryMap = new HashMap<String, String>();

        static {
            archiversMap.put(ArchiveStreamFactory.AR, "ar");
            archiversMap.put(ArchiveStreamFactory.ARJ, "arj");
            archiversMap.put(ArchiveStreamFactory.CPIO, "cpio");
            archiversMap.put(ArchiveStreamFactory.DUMP, "dump");
            archiversMap.put(ArchiveStreamFactory.JAR, "jar");
            archiversMap.put(ArchiveStreamFactory.TAR, "tar");
            archiversMap.put(ArchiveStreamFactory.ZIP, "zip");

            othersMap.put("7z", "7z");
            othersMap.put("rar", "rar");

            compressorsMap.put(CompressorStreamFactory.BZIP2, "bz2");
            compressorsMap.put(CompressorStreamFactory.GZIP, "gz");
            compressorsMap.put(CompressorStreamFactory.PACK200, "pack200");
            compressorsMap.put(CompressorStreamFactory.XZ, "xz");
            compressorsMap.put(CompressorStreamFactory.LZMA, "lzma");

            compressorFactoryMap.putAll(archiversMap);
            compressorFactoryMap.putAll(compressorsMap);
            compressorFactoryMap.putAll(othersMap);
        }
    }

}
