import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {baseUrl} from './baseApi'

export const userUrl = baseUrl + '/user'

export const userApi = createApi({
  reducerPath: 'user',
  baseQuery: fetchBaseQuery({
    baseUrl: userUrl
  }),
  endpoints: (builder) => ({
    findAuthor: builder.query({
      query: (authorName) => `find/${authorName}`
    })
  })
})

export const {useFindAuthorQuery} = userApi