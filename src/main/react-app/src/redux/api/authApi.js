import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {baseUrl} from './baseApi'

export const authUrl = baseUrl + '/auth'

export const authApi = createApi({
  reducerPath: 'auth',
  baseQuery: fetchBaseQuery({
    baseUrl: authUrl
  }),
  endpoints: (builder) => ({
    loginUser: builder.mutation({
      query: (user) => ({
        url: 'loginUser',
        method: 'POST',
        body: user
      }),
    }),
    registerUser: builder.mutation({
      query: (user) => ({
        url: 'registerUser',
        method: 'POST',
        body: user
      }),
    })
  })
})

export const {useLoginUserMutation, useRegisterUserMutation} = authApi
