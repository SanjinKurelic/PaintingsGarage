import {Button, Card, Col, Container, Row} from 'react-bootstrap'
import {hostname} from '../../redux/api/baseApi'
import {setCurrentUser} from '../../redux/slice/currentUserSlice'
import {useDispatch} from 'react-redux'
import PropTypes from 'prop-types'
import Plan from '../plan/Plan'
import {useState} from 'react'

const UserDetails = ({userDetails, showLogoutButton, className}) => {
  const dispatch = useDispatch()
  const resolvePlan = () => {
    return userDetails.plan === 'ARTIST' ? 1 : 0;
  }
  const [selectedPlan, setSelectedPlan] = useState(resolvePlan())

  return (
    <Row className={className}>
      <Col className="text-center">
        <img alt="" src={hostname + '/' + userDetails.avatar} height="128"/>
      </Col>
      <Col xs={6}>
        <Card className="user-details">
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
      <Col>
        <Card>
          <Card.Body>
            <Card.Title>Change plan</Card.Title>
            <Card.Text>
              <Plan setSelectedPlan={setSelectedPlan} selectedPlan={selectedPlan}/>
            </Card.Text>
          </Card.Body>
        </Card>
      </Col>
      <Col>
        <Button hidden={!showLogoutButton} className="user-action-button d-block m-2" variant="primary"
                onClick={() => dispatch(setCurrentUser(null))}>Logout</Button>
      </Col>
    </Row>
  )
}

UserDetails.propTypes = {
  className: PropTypes.string,
  userDetails: PropTypes.object,
  showLogoutButton: PropTypes.bool
}

UserDetails.defaultProps = {
  className: '',
  showLogoutButton: true
}

export default UserDetails
