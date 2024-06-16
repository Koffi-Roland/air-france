
// Load nœud role data
const roles = require('../doctor/ressources/records.json');
// Load expert (speciality) data
const expert = require('../doctor/ressources/expert.json');
// Load approval (air line code) data
const approval_by = require('../doctor/ressources/approval_by.json');
//Load speak (language) data
const speak = require('../doctor/ressources/speak.json');
const _ = require('lodash');
const fs = require('fs');

/**
 * Principal function used to filter and map item base on doctor role identifier
 * 
 * @param {*} list items
 * @param {*} roleId  doctor identifier
 * @returns List of item
 */
const filterByRoleIdAndMap = (list, roleId) => {
    return list.filter((item) => {
        return _.get(item, 'p.start.properties.roleId') === roleId;
    }).map((item) => _.get(item, 'p.end.properties'));
}

/**
 * Map doctor information (Specialities, approvals (air line code) and languages)
 */
const doctors = roles.map((doctor, idx) => {
    const props = doctor.n.properties;
    const expertList = filterByRoleIdAndMap(expert, props.roleId);
    const languagesList = filterByRoleIdAndMap(speak, props.roleId);
    const approval_byList = filterByRoleIdAndMap(approval_by, props.roleId);
    return {
       // id: idx,
        ...props,
        languages: languagesList,
        gin: !!props.gin ? props.gin : null,
        speciality: expertList.length ? expertList[0] : null,
        approvedBy: approval_byList.length ? approval_byList[0] : null
    };
});

/**
 * Write data in output file | throw error
 */

fs.writeFile('./doctor/result/roles.json', JSON.stringify(doctors, null, 2), {flag: 'w'}, (err) => {
    if (err)
        throw err;
    console.log("Data has been written to file successfully.");
}); 