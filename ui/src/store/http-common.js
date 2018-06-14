import axios from 'axios'

export const HTTP = axios.create({
  baseURL: 'http://ec2-35-177-96-55.eu-west-2.compute.amazonaws.com:8082/inr/'
})
