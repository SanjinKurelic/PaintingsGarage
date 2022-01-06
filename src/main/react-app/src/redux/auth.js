// For better security use HTTP only cookies and remove all auth logic from backend
// browser will automatically send cookies with every request
const ITEM_ID = 'USER-ID'

export const storeCurrentUser = (userData) => {
  localStorage.setItem(ITEM_ID, JSON.stringify(userData))
}

export const logoutCurrentUser = () => {
  localStorage.removeItem(ITEM_ID)
}

export const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem(ITEM_ID))
}

export const setAuthHeader = (headers) => {
  const user = getCurrentUser()

  if (user && user.token) {
    headers.set('Authorization', `Bearer ${user.token}`)
  }

  return headers
}
