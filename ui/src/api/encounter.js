/* eslint-disable */
import { HTTP } from '../store/http-common'
import { ukDateToInternational } from '../utilities'
import store from '../store/store';

export async function getEncounter(newEncounter) {
  let json
  HTTP.defaults.headers.common['Authorization'] = 'Bearer ' + store.state.token.access_token;
  await HTTP.get('encounter?id=' + newEncounter.identifier[0].value)
    .then((res) => {
      json = res.data
    })
    .catch((error) => {
      console.error(error)
      json = null
    })
  return json
}

export async function createEncounter(patient, startDate, diagnosis, patientContext) {
  const uuidv1 = require('uuid/v1')
  let json = {
    "id": "",
    "resourceType": "Encounter",
    "status": "in-progress",
    "identifier": [
      {
        "system": "https://fhir.leedsth.nhs.uk/Id/encounter",
        "value": uuidv1()
      }
    ],
    'subject': {
      'reference': patient.resourceType + '/' + patient.id,
      'display': patient.name[0].family,
      'identifier': {
        'system': patient.identifier[0].system,
        'value': patient.identifier[0].value
      }
    },
    "particpant": [{
      "display": patientContext.gp.name,
    }],
    'period': {
      'start': ukDateToInternational(startDate)
    },
    'reason': [{
      'text': "Created INR Treatment Plan"
    }],
    "diagnosis": [{
      "display": diagnosis,
    }],
  }
  let id
  HTTP.defaults.headers.common['Authorization'] = 'Bearer ' + store.state.token.access_token;
  await HTTP.post('encounter', json)
    .then((res) => {
      json.id = res.data.issue[0].diagnostics.match("Encounter/(.*)/_")[1]
    })
    .catch((error) => {
      console.error(error)
      json = null
    })
  return json
}