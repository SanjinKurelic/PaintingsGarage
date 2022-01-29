import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {baseUrl} from './baseApi'
import {setAuthHeader} from '../util/headerBuilder'

export const auditUrl = baseUrl + '/audit'

export const auditApi = createApi({
  reducerPath: 'audit',
  baseQuery: fetchBaseQuery({
    baseUrl: auditUrl,
    prepareHeaders: (headers, {getState}) => setAuthHeader(headers, getState())
  }),
  endpoints: (builder) => ({
    getLatestAudit: builder.query({
      query: () => `latest`
    })
  })
})

export const {useGetLatestAuditQuery} = auditApi
