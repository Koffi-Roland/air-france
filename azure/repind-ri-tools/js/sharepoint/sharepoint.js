const readXlsxFile = require('read-excel-file/node')

// File path.
readXlsxFile('./sharepoint/ressources/sharepoint.xlsx').then((rows) => {
  // `rows` is an array of rows
  // each row being an array of cells.
  const get = (row, idx) => {
      return !!row[idx] ? row[idx] : null
    }
  return rows.map((row, idx) => {
    return {
        id: idx,
        "emailAddress": get(row, 0),
        "gin": get(row, 1),
        "cin": get(row, 2),
        "firstname": get(row, 3),
        "surname": get(row, 4),
        "civility": get(row, 5),
        "birthdate": get(row, 6),
        "countryCode": get(row, 7),
        "languageCode": get(row, 8),
        "subscriptionType": get(row, 9),
        "domain": get(row, 10),
        "groupType": get(row, 11),
        "status": get(row, 12),
        "source": get(row, 13),
        "dateOfConsent": get(row, 14),
        "preferredDepartureAirport": get(row, 15),
    }
  });
}).then((result) => {
    console.log(JSON.stringify(result,null,2));
})

