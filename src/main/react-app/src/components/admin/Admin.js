import {useGetUserListQuery} from '../../redux/api/userApi'
import UserDetails from '../user/UserDetails'
import {useDispatch} from 'react-redux'
import {setCurrentUser} from '../../redux/slice/currentUserSlice'
import {Button} from 'react-bootstrap'

const Admin = () => {
  const dispatch = useDispatch()
  const userList = useGetUserListQuery()

  return (
    <>
      <Button className="user-action-button d-block m-2 float-end" variant="primary"
              onClick={() => dispatch(setCurrentUser(null))}>Logout</Button>
      <h3 className="mt-2">Activity</h3>
      <h3 className="mt-2">User list</h3>
      {userList.isSuccess && userList.data.map((userDetails) => (
        <UserDetails className="mt-2 border-2 rounded-2" userDetails={userDetails} showLogoutButton={false}/>))}
    </>
  )
}

export default Admin
