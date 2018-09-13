--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--
--insert into content.language(id,name,languageTag) values (0,'Test','Test');
CREATE OR REPLACE FUNCTION content.echo(msg text) RETURNS text AS $$ declare begin return msg; end; $$ LANGUAGE plpgsql;

insert into content.Language (id,name,languageTag) values (1,'english','en');
insert into content.Language (id,name,languageTag) values (2,'français','fr');
alter sequence content.language_seq restart with 3;

insert into content.LocalizedString(id,value, language_id) values (1,'string test',1);
insert into content.LocalizedString(id,value, language_id) values (2,'chaine de test',2);
alter sequence content.localized_string_seq restart with 3;

insert into content.InternationalizedString(id) values (1);
insert into content.internationalizedstring_localizedstring(internationalizedstring_id,values_id) values (1,1);
insert into content.internationalizedstring_localizedstring(internationalizedstring_id,values_id) values (1,2);
alter sequence content.internationalized_string_seq restart with 2;



