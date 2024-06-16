insert into Adr_post(sain,sgin,iversion,sno_et_rue,scode_postal,sville,scode_pays,scode_medium,sstatut_medium,
                     ssignature_modification,ssite_modification,ddate_modification,ssignature_creation,ssite_creation,
                     ddate_creation,sforcage,sindadr,icod_err)
values('40915315','800018352323',1,'SAMPSOUDOS 8','14565','AGIOS STEFANOS','GR','D','V','ISI','AjoutUsage',
       date '16-03-07','WEB/S08920','WEB',date '16-09-04','O','SANPSUDO',0);

insert into Usage_mediums(srin,scode_application,sain_adr,inum)
values('85200511','ISI','40915315',1);