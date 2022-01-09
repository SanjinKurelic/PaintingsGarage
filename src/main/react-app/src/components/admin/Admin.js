import {useGetUserListQuery} from '../../redux/api/userApi'
import UserDetails from '../user/UserDetails'

const Admin = () => {
  const userList = useGetUserListQuery()

  return (
    <>
      <h3 className="mt-2">User list</h3>
      {userList.isSuccess && userList.data.map((userDetails) => (
        <UserDetails className="mt-2 border-2 rounded-2" userDetails={userDetails} showLogoutButton={false}/>))}
    </>
  )
}

export default Admin
