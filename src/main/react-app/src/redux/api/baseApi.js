import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/dist/query/react'
import {setAuthHeader} from '../util/headerBuilder'

export const hostname = 'http://localhost:8080'
export const baseUrl = hostname + '/api'

export const baseApi = createApi({
  reducerPath: 'api',
  baseQuery: fetchBaseQuery({
    baseUrl: baseUrl,
    prepareHeaders: (headers, {getState}) => setAuthHeader(headers, getState())
  }),
  tagTypes: ['audit', 'photo', 'search', 'user'],
  endpoints: (builder) => ({
    // Audit
    getAuditList: builder.query({
      query: () => `audit`,
      providesTags: ['audit']
    }),
    // Auth
    loginUser: builder.mutation({
      query: (user) => ({
        url: 'auth/loginUser',
        method: 'POST',
        body: user
      }),
      invalidatesTags: ['photo', 'user', 'audit']
    }),
    registerUser: builder.mutation({
      query: (user) => ({
        url: 'auth/registerUser',
        method: 'POST',
        body: user
      }),
      invalidatesTags: ['photo', 'user', 'audit']
    }),
    // Cart
    checkout: builder.mutation({
      query: (cartItems) => ({
        url: 'cart',
        method: 'POST',
        body: cartItems
      }),
      invalidatesTags: ['photo', 'search', 'audit']
    }),
    // Photo
    getPhotoList: builder.query({
      query: () => 'photo',
      providesTags: ['photo']
    }),
    addPhoto: builder.mutation({
      query: (photo) => ({
        url: 'photo',
        method: 'POST',
        body: photo
      }),
      invalidatesTags: ['photo', 'audit']
    }),
    updatePhoto: builder.mutation({
      query: (photo) => ({
        url: `photo/${photo.id}`,
        method: 'PUT',
        body: photo
      }),
      invalidatesTags: ['photo', 'audit']
    }),
    deletePhoto: builder.mutation({
      query: (photoId) => ({
        url: `photo/${photoId}`,
        method: 'DELETE'
      }),
      invalidatesTags: ['photo', 'audit']
    }),
    // Search
    findAuthor: builder.query({
      query: (authorName) => `search/author/${authorName}`,
      providesTags: ['search']
    }),
    findHashtag: builder.query({
      query: (hashtagName) => `search/hashtag/${hashtagName}`,
      providesTags: ['search']
    }),
    findImage: builder.query({
      query: (arg) => {
        return {
          url: 'search/photo',
          params: arg
        }
      },
      providesTags: ['search']
    }),
    // User
    getUser: builder.query({
      query: () => 'user/current',
      providesTags: ['user']
    }),
    getUserList: builder.query({
      query: () => 'user',
      providesTags: ['user']
    }),
    updateUser: builder.mutation({
      query: ({userId, plan}) => ({
        url: `user/${userId}`,
        method: 'PUT',
        body: {plan: plan}
      }),
      invalidatesTags: ['user', 'audit']
    }),
  })
})

// Getters
export const {
  useGetAuditListQuery,
  useGetPhotoListQuery,
  useFindImageQuery,
  useGetUserListQuery,
  useGetUserQuery
} = baseApi
// Lazy getters
export const {
  useLazyFindHashtagQuery,
  useLazyFindImageQuery,
  useLazyFindAuthorQuery
} = baseApi
// Mutations
export const {
  useLoginUserMutation,
  useRegisterUserMutation,
  useUpdateUserMutation,
  useAddPhotoMutation,
  useCheckoutMutation,
  useUpdatePhotoMutation,
  useDeletePhotoMutation
} = baseApi
