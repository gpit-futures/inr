/* eslint-disable */
import { HTTP } from '../store/http-common'
import { ukDateToInternational } from '../utilities'
import store from '../store/store';

export async function getTreatmentPlan(patient) {
  let json
  HTTP.defaults.headers.common['Authorization'] = 'Bearer ' + store.state.token.access_token;
  await HTTP.get('careplan/associated?associatedType=Patient&id=' + patient.id)
    .then((res) => {
      json = res.data
    })
    .catch((error) => {
      console.error(error)
      json = []
    })
  return json
}

export async function createTreatmentPlan(patient, encounter, targetINR, dosingMethod, testingMethod, diagnosis, planStartDate, planEndDate, drug, patientContext) {
  const uuidv1 = require('uuid/v1')
  let json = {
    "resourceType": "CarePlan",
    "text": {
      "status": "generated",
      "div": targetINR + ',' + dosingMethod + ',' + testingMethod
    },
    "identifier": [
      {
        "system": "https://fhir.leedsth.nhs.uk/Id/careplan",
        "value": uuidv1()
      }
    ],
    "status": "active",
    "subject": {
      "reference": patient.resourceType + '/' + patient.id,
      "display": patient.name[0].family,
      "identifier": {
        "system": patient.identifier[0].system,
        "value": patient.identifier[0].value
      }
    },
    "context": {
      "reference": encounter.resourceType + '/' + encounter.id,
      "display": "Miss Bernie Kanfeld - Encounter 1",
      "identifier": {
        "system": "https://fhir.leedsth.nhs.uk/Id/encounter",
        "value": encounter.identifier[0].value
      }
    },
    "intent": "plan",
    "title": "Anti-Coagulation treatment plan",
    "period": {
      "start": ukDateToInternational(planStartDate),
      "end": ukDateToInternational(planEndDate)
    },
    "author": [{
      "reference": 'Practitioner/1',
      "display": patientContext.gp.name,
      "identifier": {
        "system": "https://fhir.leedsth.nhs.uk/Id/practitioner",
        "value": "8349583495849308590"
      }
    }],
    "addresses": [{
      "display": diagnosis,
    }],
    "activity": [
      {
        "detail": {
          "productCodeableConcept": {
            "coding": [
              {
                "system": "http://snomed.info/sct",
                "code": "372756006",
                "display": drug
              }
            ]
          },
          "status": "in-progress",
          "scheduledString": "test"
        }
      }
    ]
  }
  HTTP.defaults.headers.common['Authorization'] = 'Bearer ' + store.state.token.access_token;
  await HTTP.post('careplan', json)
    .then((res) => {
      console.log(res.data)
    })
    .catch((error) => {
      console.error(error)
      window.Bridge.sendWarningToFrame("Error: You do not have permission to create a treatment plan")
      json = null
    })
  return json
}
