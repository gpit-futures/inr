/* eslint-disable */
import { HTTP } from '../store/http-common'
import store from '../store/store';

export async function getPatient(patient) {
  let json
  HTTP.defaults.headers.common['Authorization'] = 'Bearer ' + store.state.token.access_token;
  await HTTP.get('patient?id=' + patient.identifier[0].value)
    .then((res) => {
      json = res.data
    })
    .catch((error) => {
      console.error(error)
      json = null
    })
  return json
}

export async function createPatient(patient) {
  let json
  HTTP.defaults.headers.common['Authorization'] = 'Bearer ' + store.state.token.access_token;
  await HTTP.post('patient', patient)
    .then((res) => {
      json = res.data
    })
    .catch((error) => {
      console.error(error)
      json = null
    })
  return json
}
