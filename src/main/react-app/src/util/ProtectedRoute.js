import {Redirect, Route} from 'react-router-dom'
import {useDispatch, useSelector} from 'react-redux'
import {deleteCurrentUser, selectCurrentUser} from '../redux/slice/currentUserSlice'
import {checkToken} from './TokenValidator'

const ProtectedRoute = ({componentForRole, location, ...rest}) => {
  const currentUser = useSelector(selectCurrentUser)
  const dispatch = useDispatch()

  if (!checkToken(currentUser)) {
    dispatch(deleteCurrentUser())
    return (<Redirect to={{pathname: '/login', state: {from: location}}}/>)
  }

  const role = currentUser.user.role.toLowerCase().replace('role_', '')
  return (
    <Route {...rest} component={componentForRole[role]}/>
  )
}

export default ProtectedRoute
