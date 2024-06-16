insert into Communication_preferences(com_pref_id,sgin,com_group_type,com_type,subscribe,domain,creation_date,creation_signature,
                                      creation_site,date_optin,modification_date,modification_signature,modification_site)
values(36654314, '110001017463', 'S', 'UL_PS', 'N', 'U', timestamp '18-01-17 13:34:51.331000000', 'CBS', 'QVI',
       timestamp '30-05-17 15:51:58.236000000', timestamp '30-05-17 15:51:58.236000000', 'REPIND', 'QVI');

insert into Communication_preferences(com_pref_id,sgin,com_group_type,com_type,subscribe,
                                      domain,creation_date,creation_signature,creation_site,date_optin,modification_date,
                                      modification_signature,modification_site,channel)
values(27598590, '110001017463', 'N', 'AF', 'Y', 'S', timestamp '18-09-14 16:05:58.198000000', 'ALIM-WW-BATCH', 'VLB',
       timestamp '1992-06-25 12:00:00.000000000', timestamp '14-09-21 08:17:28.789000000', 'REPIND/IHM', 'QVI', 'FB_Migration');


insert into Market_language(market_language_id,com_pref_id,creation_date,creation_signature,creation_site,date_optin,
                            language_code,market,modification_date,modification_signature,modification_site,optin)
values(22348883, 36654314, timestamp '18-01-17 13:34:51.331000000', 'CBS', 'QVI', timestamp '18-01-17 13:37:48.000000000',
       'FR', 'FR', timestamp '18-01-17 13:39:28.641000000', 'CBS', 'QVI', 'N');

insert into Market_language(market_language_id,com_pref_id,creation_date,creation_signature,creation_site,
                            date_optin,language_code,market,modification_date,modification_signature,modification_site,optin)
values(13553966, 27598590, timestamp '18-09-14 16:05:58.198000000', 'ALIM-WW-BATCH', 'VLB', timestamp '1992-06-25 12:00:00.000000000',
       'FR', 'FR', timestamp '14-09-21 08:17:28.789000000', 'REPIND/IHM', 'QVI', 'Y')