CREATE TABLE `uaa`.`oauth_user`(
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(64) NOT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `activated` tinyint(4) NOT NULL DEFAULT '0',
  `created_by` bigint(11) DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` bigint(11) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `memo` varchar(255) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

select client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove from oauth_client_details
select code, authentication from oauth_code
select token_id, token, authentication_id, user_name, client_id, authentication, refresh_token from oauth_access_token
select token_id, token, authentication from oauth_refresh_token
select token_id, token, authentication_id, user_name, client_id from oauth_client_token
select expiresAt,status,lastModifiedAt,userId,clientId,scope from oauth_approvals

