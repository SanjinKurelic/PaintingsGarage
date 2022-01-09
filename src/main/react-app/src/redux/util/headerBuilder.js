export const setAuthHeader = (headers, getState) => {
  const userData = getState().currentUser

  if (userData && userData.user && userData.user.token) {
    headers.set('Authorization', `Bearer ${userData.user.token}`)
  }

  return headers
}
