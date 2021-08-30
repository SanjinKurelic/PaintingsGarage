import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {baseUrl} from './baseApi'

export const searchUrl = baseUrl + '/search';

export const searchApi = createApi({
  reducerPath: 'search',
  baseQuery: fetchBaseQuery({
    baseUrl: searchUrl
  }),
  endpoints: (builder) => ({
    getLatestImages: builder.query({
      query: () => 'image/latest'
    })
  })
})

export const {useGetLatestImagesQuery} = searchApi