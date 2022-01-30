import UserDetails from '../user/UserDetails'
import {useDispatch} from 'react-redux'
import {deleteCurrentUser} from '../../redux/slice/currentUserSlice'
import {Button, Col, Row} from 'react-bootstrap'
import {BiImageAlt, BiLockAlt, BiUser} from 'react-icons/all'
import moment from 'moment'
import './admin.scss'
import {useGetAuditListQuery, useGetUserListQuery} from '../../redux/api/baseApi'

const Admin = () => {
  const dispatch = useDispatch()
  const userList = useGetUserListQuery()
  const auditList = useGetAuditListQuery()

  return (
    <div className="admin">
      <Button className="user-action-button d-block m-2 position-absolute end-0" variant="primary"
              onClick={() => dispatch(deleteCurrentUser)}>Logout</Button>
      <h3 className="m-5 text-center">Activity</h3>
      <div className="admin-audit">
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
      <div className="admin-users">
        {userList.isSuccess && userList.data.map((userDetails) => (
          <UserDetails className="mt-2 border-2 rounded-2" userDetails={userDetails} showLogoutButton={false}/>))}
      </div>
    </div>
  )
}

export default Admin
