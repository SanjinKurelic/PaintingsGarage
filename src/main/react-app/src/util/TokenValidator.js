export const checkToken = (currentUser) => {
  // Check if token exists and contains valid format
  if (!currentUser || !currentUser.user || !currentUser.user.token || currentUser.user.token.split('.') < 1) {
    return false
  }

  // Check if token has expired
  let jwt = JSON.parse(atob(currentUser.user.token.split('.')[1]))
  return jwt.exp * 1000 >= Date.now()
}
