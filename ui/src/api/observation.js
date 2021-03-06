/* eslint-disable */
import { HTTP } from '../store/http-common'
import { ukDateToInternational, ukDateTimeToInternational } from '../utilities'
import store from '../store/store';

export async function getObservation(treatmentPlan) {
    let json
    HTTP.defaults.headers.common['Authorization'] = 'Bearer ' + store.state.token.access_token;
    await HTTP.get('observation/associated?associatedType=' + treatmentPlan.context.reference.replace('/', '&id='))
        .then((res) => {
            json = res.data
        })
        .catch((error) => {
            console.error(error)
            json = null
        })
    return json
}

export async function updateObservation(observation) {
    let json = observation
    HTTP.defaults.headers.common['Authorization'] = 'Bearer ' + store.state.token.access_token;
    await HTTP.put('observation', json)
        .then((res) => {
        })
        .catch((error) => {
            console.error(error)
            json = null
        })
    return json
}

export async function createObservation(patient, selectedPlan, patientContext, test) {
    const uuidv1 = require('uuid/v1')
    let json = {
        "resourceType": "Observation",
        "identifier": [
            {
                "system": "https://fhir.leedsth.nhs.uk/Id/observation",
                "value": uuidv1()
            }
        ],
        "text": {
            "status": "generated",
            "div": test.dose + ',' + test.reviewDays + ',' + ukDateToInternational(test.nextTestDate)
        },
        "status": "final",
        "category": [
            {
                "coding": [
                    {
                        "code": "laboratory",
                        "display": "laboratory",
                        "system": "http://hl7.org/fhir/observation-category"
                    }
                ]
            }
        ],
        "code": {
            "coding": [
                {
                    "code": "313341008",
                    "display": "International Normalized Ratio raised",
                    "system": "http://snomed.info/sct"
                }
            ]
        },
        "subject": {
            'reference': patient.resourceType + '/' + patient.id,
            'display': patient.name[0].family,
            'identifier': {
                'system': patient.identifier[0].system,
                'value': patient.identifier[0].value
            }
        },
        "context": {
            'reference': selectedPlan.context.reference,
            "display": patient.name[0].family + ' - Encounter',
            "identifier": {
                "system": selectedPlan.context.identifier.system,
                "value": selectedPlan.context.identifier.value
            }
        },
        "effectiveDateTime": ukDateTimeToInternational(test.testDate, test.testTime),
        "performer": [
            {
                "display": patientContext.gp.name
            }
        ],
        "valueQuantity": {
            "code": "10*9/L",
            "system": "http://unitsofmeasure.org",
            "unit": "10*9/L",
            "value": test.inr
        },
        "interpretation": {
            "coding": [
                {
                    "code": "N",
                    "display": "Normal",
                    "system": "http://hl7.org/fhir/v2/0078"
                }
            ]
        },
        "referenceRange": [
            {
                "high": {
                    "value": 10
                },
                "low": {
                    "value": 3.5
                }
            }
        ]
    }
    HTTP.defaults.headers.common['Authorization'] = 'Bearer ' + store.state.token.access_token;
    await HTTP.post('observation', json)
        .then((res) => {
        })
        .catch((error) => {
            console.error(error)
            window.Bridge.sendWarningToFrame("Error: You do not have permission to create an INR reading")
            json = null
        })
    return json
}