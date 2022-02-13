import {Button, Card, Col, Container, Row} from 'react-bootstrap'
import {hostname} from '../../redux/api/baseApi'
import {deleteCurrentUser} from '../../redux/slice/currentUserSlice'
import {useDispatch} from 'react-redux'
import PropTypes from 'prop-types'
import Plan from '../plan/Plan'
import {useEffect, useState} from 'react'
import {useHistory} from 'react-router-dom'

const UserDetails = ({userDetails, showLogoutButton, className}) => {
  const dispatch = useDispatch()
  const history = useHistory()
  const resolvePlan = (plan) => {
    return plan === 'ARTIST' ? 1 : 0
  }
  const [selectedPlan, setSelectedPlan] = useState(resolvePlan(userDetails.plan))
  // If plan change, set new one
  useEffect(() => {
    setSelectedPlan(resolvePlan(userDetails.plan))
  }, [userDetails.plan])

  const logout = () => {
    dispatch(deleteCurrentUser())
    history.push('/')
    // Fix for fetching the latest images
    window.location.reload()
  }

  return (
    <Row className={className}>
      <Col className="text-center col-2">
        <img className="mt-3" alt="" src={hostname + '/' + userDetails.avatar} height="100"/>
      </Col>
      <Col className={showLogoutButton ? 'col-6' : 'col-7'}>
        <Card className="text-start">
          <Card.Body>
            <Card.Title>User details</Card.Title>
            <Card.Text>
              <Container>
                <Row>
                  <Col className="fw-bold">Name:</Col>
                  <Col>{userDetails.name}</Col>
                </Row>
                <Row>
                  <Col className="fw-bold">Email:</Col>
                  <Col>{userDetails.email}</Col>
                </Row>
                <Row>
                  <Col className="fw-bold">Date of registration:</Col>
                  <Col>{userDetails.registered}</Col>
                </Row>
              </Container>
            </Card.Text>
          </Card.Body>
        </Card>
      </Col>
      <Col className={showLogoutButton ? 'col-2' : 'col-3'}>
        <Card>
          <Card.Body>
            <Card.Title>Change plan</Card.Title>
            <Card.Text>
              <Plan userId={userDetails.id} setSelectedPlan={setSelectedPlan} selectedPlan={selectedPlan}/>
            </Card.Text>
          </Card.Body>
        </Card>
      </Col>
      {showLogoutButton && <Col className="col-2"><Button className="button-dark d-block m-2" variant="primary"
                                                          onClick={() => logout()}>Logout</Button></Col>}
    </Row>
  )
}

UserDetails.propTypes = {
  className: PropTypes.string,
  userDetails: PropTypes.shape({
    id: PropTypes.number.isRequired,
    plan: PropTypes.oneOf(['ARTIST', 'BUYER']),
    avatar: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    email: PropTypes.string.isRequired,
    registered: PropTypes.string.isRequired
  }).isRequired,
  showLogoutButton: PropTypes.bool
}

UserDetails.defaultProps = {
  className: '',
  showLogoutButton: true
}

export default UserDetails
