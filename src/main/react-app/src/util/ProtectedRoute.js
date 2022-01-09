import {Redirect, Route} from 'react-router-dom'
import {useAuth} from '../hooks/useAuth'
import {useDispatch} from 'react-redux'
import {setCurrentUser} from '../redux/slice/currentUserSlice'

const ProtectedRoute = ({component, location, ...rest}) => {
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

  return (
    <Route {...rest} component={component}/>
  )
}

export default ProtectedRoute
