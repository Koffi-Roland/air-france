const gremlin = require('gremlin');
const config = require("./config");
const roles = require('../roles.json')
const languages = require('./languages.json');
const specs = require('../specs.json');
const edgeSpeak = require('./edgeSpeak.json');
const edgeExpert = require('./edgeExpert.json');
const edgeApprovedBy = require('./edgeApprovedBy.json');

const authenticator = new gremlin.driver.auth.PlainTextSaslAuthenticator(`/dbs/${config.database}/colls/${config.collection}`, config.primaryKey)

const client = new gremlin.driver.Client(
    config.endpoint, 
    { 
        authenticator,
        traversalsource : "g",
        rejectUnauthorized : true,
        mimeType : "application/vnd.gremlin-v2.0+json"
    }
);


function dropGraph()
{
    console.log('Running Drop');
    return client.submit('g.V().drop()', { }).then(function (result) {
        console.log("Result: %s\n", JSON.stringify(result));
    });
}

function addRole()
{
    console.log('roles', roles);
    const promises = roles.map((role) => {
        return client.submit(`g.addV("role").property('type', "${role.type}").property('id', "${role.id}").property('doctorStatus', "${role.doctorStatus}").property('doctorId', "${role.doctorId}").property('signatureDateCreation', "${role.signatureDateCreation}").property('endDateRole', "${role.endDateRole}").property('roleId',"${role.roleId}").property("lastUpdate","${role.lastUpdate}").property("signatureSourceCreation","${role.signatureSourceCreation}").property("siteCreation","${role.siteCreation}").property("gin","${role.gin}")`
        ).then(function (result) {
                console.log("Result: %s\n", JSON.stringify(result));
        });
    });
    return Promise.all(promises);
}

function addLanguage()
{
    console.log('languages', languages);
    const promises = languages.map((language) => {
        return client.submit(`g.addV("language").property('type', "${language.type}").property('id', "${language.id}").property('acronyme', "${language.acronyme}")`
        ).then(function (result) {
                console.log("Result: %s\n", JSON.stringify(result));
        });
    });
    return Promise.all(promises);
}

function addSpeciality()
{
    console.log('specs', specs);
    const promises = specs.map((spec) => {
        return client.submit(`g.addV("Speciality").property('type', "${spec.type}").property('id', "${spec.id}").property('value', "${spec.value}")`
        ).then(function (result) {
                console.log("Result: %s\n", JSON.stringify(result));
        });
    });
    return Promise.all(promises);
}

function addEdge(list, relation)
{
    console.log('edges', list);
    const promises = list.map((item) => {
        return client.submit(`g.V('id','${item.start}').addE('${relation}').to(g.V('id', '${item.end}'))`).then(function (result) {
            console.log("Result: %s\n", JSON.stringify(result));
        });
    })
    return Promise.all(promises);
}

function countVertices()
{
    console.log('Running Count');
    return client.submit("g.V().count()", { }).then(function (result) {
        console.log("Result: %s\n", JSON.stringify(result));
    });
}

function finish()
{
    console.log("Finished");
    console.log('Press any key to exit');
    
    process.stdin.resume();
    process.stdin.on('data', process.exit.bind(process, 0));
}

client.open()
    .then(dropGraph)
    .then(addRole)
    .then(addLanguage)
    .then(addSpeciality)
    .then(() => {
        return addEdge(edgeSpeak, 'speak')
    })
    .then(() => {
        return addEdge(edgeExpert, 'expert')
    })
    .then(() => {
        return addEdge(edgeApprovedBy, 'approvedBy')
    })
    .then(countVertices)
    .catch((err) => {
        console.error("Error running query...");
        console.error(err)
    }).then((res) => {
        client.close();
        finish();
    }).catch((err) => 
        console.error("Fatal error:", err)
    );