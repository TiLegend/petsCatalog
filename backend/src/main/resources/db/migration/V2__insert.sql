INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity, additional_information)
VALUES
('web', 'secret', 'read', 'authorization_code,password,refresh_token,implicit', '900', '{}');

-- password = 'pass'
INSERT INTO users (id, password, username) VALUES ('	1	', '$2a$10$IYmJnkSCJ.F0whwhi5R3A.SIAZI/wObzE11MTluz21rVh0BCWQhtO', 'test');

INSERT INTO public.pet_types(pet_type_name)	VALUES ('cat');
INSERT INTO public.pet_types(pet_type_name)	VALUES ('dog');
INSERT INTO public.pet_types(pet_type_name)	VALUES ('fish');
INSERT INTO public.pet_types(pet_type_name)	VALUES ('snake');