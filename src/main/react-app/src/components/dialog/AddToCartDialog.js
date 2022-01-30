import {Button, Col, ListGroup, ModalBody, ModalDialog, ModalFooter, ModalTitle, Row} from 'react-bootstrap'
import ModalHeader from 'react-bootstrap/ModalHeader'
import PropTypes from 'prop-types'
import {useDispatch} from 'react-redux'
import {hideDialog} from '../../redux/slice/currentDialogSlice'
import {useState} from 'react'
import canvas from '../../assets/canvas.png'
import camera from '../../assets/camera.png'
import './addToCartDialog.scss'

const AddToCartDialog = ({image}) => {
  const dispatch = useDispatch()
  const [pictureType, setPictureType] = useState('DIGITAL')

  const add = () => {
    console.log(image)
    console.log(pictureType)
    dispatch(hideDialog())
  }

  const cancel = () => {
    dispatch(hideDialog())
  }

  return (
    <div className="position-fixed top-0 bottom-0 w-100 dialog-box">
      <ModalDialog centered={true}>
        <ModalHeader>
          <ModalTitle>Add to Cart</ModalTitle>
        </ModalHeader>
        <ModalBody>
          <Row>
            <Col className="col-4">Image type:</Col>
            <Col>
              <ListGroup horizontal>
                <ListGroup.Item className="cart-dialog-item text-center" data-selected={pictureType === 'DIGITAL'}
                                onClick={() => setPictureType('DIGITAL')}>
                  <img height="50px" alt="Digital" src={camera}/>
                </ListGroup.Item>
                <ListGroup.Item className="cart-dialog-item text-center" data-selected={pictureType === 'PHYSICAL'}
                                onClick={() => setPictureType('PHYSICAL')}>
                  <img height="50px" alt="Physical" src={canvas}/>
                </ListGroup.Item>
              </ListGroup>
            </Col>
          </Row>
          <Row>
            <Col className="col-4"/>
            <Col>
              <p><small>Physical copy will also contain digital copy.</small></p>
            </Col>
          </Row>
          <Row>
            <Col className="col-4">Price:</Col>
            <Col>{pictureType === 'DIGITAL' ? image.digitalPrice : image.paintingPrice} €</Col>
          </Row>
        </ModalBody>
        <ModalFooter>
          <Button className="dialog-box-button" onClick={cancel}>Cancel</Button>
          <Button className="dialog-box-button" onClick={add}>Add to cart</Button>
        </ModalFooter>
      </ModalDialog>
    </div>
  )
}

AddToCartDialog.propTypes = {
  image: PropTypes.object.isRequired
}

export default AddToCartDialog
