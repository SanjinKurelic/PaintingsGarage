export const setAuthHeader = (headers, state) => {
  const userData = state.currentUser
  const authHeader = 'Authorization';

  if (userData && userData.user && userData.user.token) {
    headers.set(authHeader, `Bearer ${userData.user.token}`)
  }
  else if (headers.has(authHeader)) {
    headers.delete(authHeader)
  }

  return headers
}
