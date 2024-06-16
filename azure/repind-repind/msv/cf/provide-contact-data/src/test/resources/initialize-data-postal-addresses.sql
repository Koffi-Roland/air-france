insert into individus_all (sgin, iversion, stype, ddate_creation, ssignature_creation, ssite_creation, snon_fusionnable,
                           ssexe, scivilite, scie_gest, ddate_naissance, sstatut_individu, scode_titre, snationalite,
                           sautre_nationalite, snom_typo, sprenom_typo, snom_typo2, sprenom_typo2, ssite_modification,
                           ssignature_modification, ddate_modification, ssite_fraudeur, ssignature_fraudeur,
                           ddate_mod_fraudeur, ssite_mot_passe, ssignature_mot_passe, ddate_mod_mot_de_passe,
                           sfraudeur_carte_bancaire, stier_utilise_comme_piege, salias_nom1, salias_nom2, salias_pre1,
                           salias_pre2, salias_civ1, salias_civ2, sindict_np, sindict_nom, sindcons, sgin_fusion,
                           ddate_fusion, salias_prenom, ssecond_prenom, sprenom, salias, snom, smot_de_passe,
                           sprov_amex, sidentifiant_personnel)
values ('800018352323', 1, 'I', '2023-06-15', 'RI', 'QVI', 'N', 'M', 'MR', 'ABC', '2023-06-15', 'V', '', '', '', null,
        null, null, null, 'QVI', 'RI', '2023-06-15', 'QVI', 'RI', '2023-06-15', 'QVI', 'RI', '2023-06-15', null, null,
        '', '', '', '', null, null, null, '', '2023-06-15', '', '2023-06-15', 'Jean', '', 'Poulet', '', '', '', '', '');

insert into Adr_post(sain, sgin, iversion, sno_et_rue, scode_postal, sville, scode_pays, scode_medium, sstatut_medium,
                     ssignature_modification, ssite_modification, ddate_modification, ssignature_creation,
                     ssite_creation,
                     ddate_creation, sforcage, sindadr, icod_err)
values ('40915315', '800018352323', 1, 'SAMPSOUDOS 8', '14565', 'AGIOS STEFANOS', 'GR', 'D', 'V', 'ISI', 'AjoutUsage',
        date '16-03-07', 'WEB/S08920', 'WEB', date '16-09-04', 'O', 'SANPSUDO', 0);

insert into Usage_mediums(srin, scode_application, sain_adr, inum)
values ('85200511', 'ISI', '40915315', 1);
