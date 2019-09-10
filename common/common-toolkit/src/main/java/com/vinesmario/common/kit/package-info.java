package com.vinesmario.common.kit;

//在多线程中使用同一个静态方法时，每个线程使用各自的实例字段(instance field)的副本，而共享一个静态字段(static field)。
//所以说，如果该静态方法不去操作一个静态成员，只在方法内部使用实例字段(instance field)，不会引起安全性问题。
//但是，如果该静态方法操作了一个静态变量，则需要静态方法中采用互斥访问的方式进行安全处理。
//为了实现互斥访问，这时需要加入一个synchronized关键字。