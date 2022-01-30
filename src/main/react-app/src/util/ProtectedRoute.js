import {Redirect, Route} from 'react-router-dom'
import {useDispatch, useSelector} from 'react-redux'
import {deleteCurrentUser, selectCurrentUser} from '../redux/slice/currentUserSlice'

const ProtectedRoute = ({componentForRole, location, ...rest}) => {
  const currentUser = useSelector(selectCurrentUser)
  const dispatch = useDispatch()

  const checkToken = () => {
    // Check if token exists and contains valid format
    if (!currentUser || !currentUser.user || !currentUser.user.token || currentUser.user.token.split('.') < 1) {
      return false
    }

    // Check if token has expired
    let jwt = JSON.parse(atob(currentUser.user.token.split('.')[1]))
    if (jwt.exp * 10000 < Date.now()) {
      dispatch(deleteCurrentUser())
      return false
    }

    return true
  }

  if (!checkToken()) {
    return (<Redirect to={{pathname: '/login', state: {from: location}}}/>)
  }

  const role = currentUser.user.role.toLowerCase().replace("role_", "")
  return (
    <Route {...rest} component={componentForRole[role]}/>
  )
}

export default ProtectedRoute
