import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {baseUrl} from './baseApi'
import {setAuthHeader} from '../util/headerBuilder'

export const photoUrl = baseUrl + '/photo'

export const photoApi = createApi({
  reducerPath: 'photo',
  baseQuery: fetchBaseQuery({
    baseUrl: photoUrl,
    prepareHeaders: (headers, {getState}) => setAuthHeader(headers, getState())
  }),
  tagTypes: ['Photos'],
  endpoints: (builder) => ({
    getLatestImages: builder.query({
      query: () => 'latest',
      providesTags: ['Photos']
    }),
    findImages: builder.query({
      query: (arg) => {
        return {
          url: 'find',
          params: arg
        }
      },
      providesTags: ['Photos']
    }),
    uploadPhoto: builder.mutation({
      query: (photo) => ({
        url: 'upload',
        method: 'POST',
        body: photo
      }),
      invalidatesTags: ['Photos']
    })
  })
})

export const {useGetLatestImagesQuery, useLazyFindImagesQuery, useFindImagesQuery} = photoApi
