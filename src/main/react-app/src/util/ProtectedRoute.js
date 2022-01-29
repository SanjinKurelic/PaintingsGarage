import {Redirect, Route} from 'react-router-dom'
import {useAuth} from '../hooks/useAuth'
import {useDispatch} from 'react-redux'
import {setCurrentUser} from '../redux/slice/currentUserSlice'

const ProtectedRoute = ({componentForRole, location, ...rest}) => {
  const {user} = useAuth()
  const dispatch = useDispatch()

  const checkToken = () => {
    // Check if token exists and contains valid format
    if (!user || !user.token || user.token.split('.') < 1) {
      return false
    }

    // Check if token has expired
    let jwt = JSON.parse(atob(user.token.split('.')[1]))
    if (jwt.exp * 10000 < Date.now()) {
      dispatch(setCurrentUser(null))
      return false
    }

    return true
  }

  if (!checkToken()) {
    return (<Redirect to={{pathname: '/login', state: {from: location}}}/>)
  }

  const role = user.role.toLowerCase().replace("role_", "")
  return (
    <Route {...rest} component={componentForRole[role]}/>
  )
}

export default ProtectedRoute
