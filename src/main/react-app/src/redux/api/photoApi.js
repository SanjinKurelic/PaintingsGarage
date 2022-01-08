import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {baseUrl} from './baseApi'
import {setAuthHeader} from '../util/headerBuilder'

export const photoUrl = baseUrl + '/photo'

export const photoApi = createApi({
  reducerPath: 'photo',
  baseQuery: fetchBaseQuery({
    baseUrl: photoUrl,
    prepareHeaders: (headers, {getState}) => setAuthHeader(headers, getState)
  }),
  endpoints: (builder) => ({
    getLatestImages: builder.query({
      query: () => 'latest'
    }),
    findImages: builder.query({
      query: (arg) => {
        return {
          url: 'find',
          params: arg
        }
      }
    })
  })
})

export const {useGetLatestImagesQuery, useLazyFindImagesQuery, useFindImagesQuery} = photoApi
