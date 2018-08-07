import moment from 'moment-es6'

let internationalDateToUk = (date) => {
  return moment(date, 'YYYY-MM-DD').format('DD-MMM-YYYY')
}

let ukDateToInternational = (date) => {
  return moment(date, 'DD-MMM-YYYY').format('YYYY-MM-DD')
}

let ukDateTimeToInternational = (date, time) => {
  return moment(date + ' ' + time, 'DD-MMM-YYYY').format('YYYY-MM-DD, hh:mm')
}

let ukDateAddDays = (date, days) => {
  return moment(date, 'DD-MMM-YYYY').add(days, 'days').format('DD-MMM-YYYY')
}

export { internationalDateToUk, ukDateAddDays, ukDateToInternational, ukDateTimeToInternational }
