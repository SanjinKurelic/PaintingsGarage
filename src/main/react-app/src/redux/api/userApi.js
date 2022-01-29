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
    }),
    getUserList: builder.query({
      query: () => 'list'
    }),
    changePlan: builder.mutation({
      query: (plan) => ({
        url: 'changePlan',
        method: 'POST',
        body: {plan: plan}
      })
    })
  })
})

export const {useLazyFindAuthorQuery, useLazyGetUserDetailsQuery, useGetUserListQuery, useChangePlanMutation} = userApi
