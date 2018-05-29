import { HTTP } from '../store/http-common'
import { ukDateToInternational } from '../utilities'

export async function getEncounter(newEncounter) {
  let json
  await HTTP.get('encounter?id=' + newEncounter.identifier[0].value)
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

export async function createEncounter(patient, startDate, diagnosis, patientContext) {
  const uuidv1 = require('uuid/v1')
  let json = {
    "id":"",
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
  await HTTP.post('encounter', json)
    .then((res) => {
      console.log(res.data)
      json.id = res.data.issue[0].diagnostics.match("Encounter/(.*)/_")[1]
      console.log(json.id)
    })
    .catch((error) => {
      console.error(error)
      json = null
    })
  return json
}