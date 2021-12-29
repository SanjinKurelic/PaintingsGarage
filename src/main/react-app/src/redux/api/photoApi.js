import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {baseUrl} from './baseApi'

export const photoUrl = baseUrl + '/photo'

export const photoApi = createApi({
  reducerPath: 'photo',
  baseQuery: fetchBaseQuery({
    baseUrl: photoUrl
  }),
  endpoints: (builder) => ({
    getLatestImages: builder.query({
      query: () => 'latest'
    })
  })
})

export const {useGetLatestImagesQuery} = photoApi