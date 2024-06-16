const readXlsxFile = require('read-excel-file/node')

// File path.
readXlsxFile('./excel/ressources/gins.xlsx').then((rows) => {
  // `rows` is an array of rows
  // each row being an array of cells.
  return rows.map((row) => {
    return `'${row[0]}'`;
  });
}).then((ids) => {
    const first = ids.splice(0,900);
    const second = ids.splice(0,900);
    const third = ids.splice(0, 900);
    [first, second, third].forEach((list) => {
        const request = `SELECT SGIN FROM ROLE_CONTRATS where in (${list.join(',')});`;
        console.log(request);
        console.log('##########################################################################');
    });
})

