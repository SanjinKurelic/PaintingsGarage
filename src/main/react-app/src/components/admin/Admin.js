import UserDetails from '../user/UserDetails'
import {useDispatch} from 'react-redux'
import {deleteCurrentUser} from '../../redux/slice/currentUserSlice'
import {Button, Col, Row} from 'react-bootstrap'
import {BiImageAlt, BiLockAlt, BiUser} from 'react-icons/all'
import moment from 'moment'
import './admin.scss'
import {useGetAuditListQuery, useGetUserListQuery} from '../../redux/api/baseApi'
import {useHistory} from 'react-router-dom'

const Admin = () => {
  const dispatch = useDispatch()
  const history = useHistory()
  const userList = useGetUserListQuery()
  const auditList = useGetAuditListQuery()

  const logout = () => {
    dispatch(deleteCurrentUser())
    history.push('/')
    // Fix for fetching the latest images
    window.location.reload()
  }

  return (
    <div className="admin position-relative">
      <Button className="button-dark d-block m-2 position-absolute end-0" variant="primary"
              onClick={() => logout()}>Logout</Button>
      <h3 className="m-5 text-center">Activity</h3>
      <div className="admin-audit position-relative m-auto">
        {auditList.isSuccess && auditList.data.map((auditDetail) => (
          <Row className="m-3">
            <Col className="col-1 rounded-circle text-center m-2 admin-audit-action">
              {auditDetail.type === 'AUTH_TYPE' && <BiLockAlt/>}
              {auditDetail.type === 'IMAGE_TYPE' && <BiImageAlt/>}
              {auditDetail.type === 'USER_TYPE' && <BiUser/>}
            </Col>
            <Col className="col-8 admin-audit-message">{auditDetail.message}</Col>
            <Col className="text-end admin-audit-time">
              <i>{moment(auditDetail.time).format('YYYY-MM-DD')}</i>,&nbsp;
              <i>{moment(auditDetail.time).format('HH:mm')}</i>
            </Col>
          </Row>
        ))}
        {(!auditList.isSuccess || auditList.data.length < 1) &&
          <p className="alert alert-warning">No activity found</p>}
      </div>
      <h3 className="m-5 text-center">User list</h3>
      <div className="admin-users m-auto">
        {userList.isSuccess && userList.data.map((userDetails) => (
          <UserDetails className="mt-2 border-2 rounded-2" userDetails={userDetails} showLogoutButton={false}/>))}
      </div>
    </div>
  )
}

export default Admin
