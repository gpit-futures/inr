import { HTTP } from '../store/http-common'

export async function getPatient(patient) {
  let json
  await HTTP.get('patient?id=' + patient.identifier[0].value)
    .then((res) => {
      console.log(res.data)
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
  await HTTP.post('patient', patient)
    .then((res) => {
      console.log(res.data)
      json = res.data
    })
    .catch((error) => {
      console.error(error)
      json = null
    })
  return json
}
