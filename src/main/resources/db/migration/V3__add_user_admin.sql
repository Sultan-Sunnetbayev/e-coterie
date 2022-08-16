INSERT INTO `users`(`name`,`surname`,`email`,`password`,`status`,`role_id`) VALUES('admin',
                                                                                  'adminow',
                                                                                  'admin@admin.com',
                                                                                  '$2a$10$fmU38YOLOp48onpfCpKl7eu7vxEVmvIN6b4D28VlYusV.EBwHrZbW',
                                                                                  true,
                                                                                  (SELECT `id` FROM `roles` WHERE `name`='ROLE_ADMIN'));