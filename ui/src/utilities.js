import moment from 'moment-es6'

let internationalDateToUk = (value) => {
  return moment(value, 'YYYY-MM-DD').format('DD-MMM-YYYY')
}

export { internationalDateToUk }
