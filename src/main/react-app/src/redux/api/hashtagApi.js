import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {baseUrl} from './baseApi'

export const hashtagUrl = baseUrl + '/hashtag'

export const hashtagApi = createApi({
  reducerPath: 'hashtag',
  baseQuery: fetchBaseQuery({
    baseUrl: hashtagUrl
  }),
  endpoints: (builder) => ({
    findHashtag: builder.query({
      query: (hashtagName) => `find/${hashtagName}`
    })
  })
})

export const {useLazyFindHashtagQuery} = hashtagApi
