export const setAuthHeader = (headers, state) => {
  const userData = state.currentUser

  if (userData && userData.user && userData.user.token) {
    headers.set('Authorization', `Bearer ${userData.user.token}`)
  }

  return headers
}
