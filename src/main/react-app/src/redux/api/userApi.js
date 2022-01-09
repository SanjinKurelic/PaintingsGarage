import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {baseUrl} from './baseApi'
import {setAuthHeader} from '../util/headerBuilder'

export const userUrl = baseUrl + '/user'

export const userApi = createApi({
  reducerPath: 'user',
  baseQuery: fetchBaseQuery({
    baseUrl: userUrl,
    prepareHeaders: (headers, {getState}) => setAuthHeader(headers, getState())
  }),
  endpoints: (builder) => ({
    findAuthor: builder.query({
      query: (authorName) => `find/${authorName}`
    }),
    getUserDetails: builder.query({
      query: () => 'details'
    })
  })
})

export const {useLazyFindAuthorQuery, useGetUserDetailsQuery} = userApi
