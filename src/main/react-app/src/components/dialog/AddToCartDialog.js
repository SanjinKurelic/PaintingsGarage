import {Button, Col, ListGroup, ModalBody, ModalDialog, ModalFooter, ModalTitle, Row} from 'react-bootstrap'
import ModalHeader from 'react-bootstrap/ModalHeader'
import PropTypes from 'prop-types'
import {useDispatch} from 'react-redux'
import {hideDialog} from '../../redux/slice/currentDialogSlice'
import {useState} from 'react'
import canvas from '../../assets/canvas.png'
import camera from '../../assets/camera.png'
import {useCart} from 'react-use-cart'

const AddToCartDialog = ({image}) => {
  const dispatch = useDispatch()
  const {addItem, inCart} = useCart()
  const [pictureType, setPictureType] = useState('DIGITAL')

  const add = () => {
    console.log(image)
    const price = pictureType === 'DIGITAL' ? image.digitalPrice : image.paintingPrice
    addItem({id: image.id, price: price, data: image, pictureType: pictureType})
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
        {inCart(image.id) && <ModalBody>
          <p>An item is already in the cart.</p>
        </ModalBody>}
        {!inCart(image.id) && <ModalBody>
          <Row>
            <Col className="col-4">Image type:</Col>
            <Col>
              <ListGroup horizontal>
                <ListGroup.Item className="switcher-item text-center"
                                data-selected={pictureType === 'DIGITAL'}
                                onClick={() => setPictureType('DIGITAL')}>
                  <img height="50px" alt="Digital" src={camera}/>
                </ListGroup.Item>
                <ListGroup.Item className="switcher-item text-center"
                                data-selected={pictureType === 'PHYSICAL'}
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
            <Col>{pictureType === 'DIGITAL' ?
              image.digitalPrice.toLocaleString(undefined, {minimumFractionDigits: 2}) :
              image.paintingPrice.toLocaleString(undefined, {minimumFractionDigits: 2})
            } €</Col>
          </Row>
        </ModalBody>}
        <ModalFooter>
          <Button className="button-dark" onClick={cancel}>{!inCart(image.id) ? 'Cancel' : 'Ok'}</Button>
          {!inCart(image.id) && <Button className="button-dark" onClick={add}>Add to cart</Button>}
        </ModalFooter>
      </ModalDialog>
    </div>
  )
}

AddToCartDialog.propTypes = {
  image: PropTypes.object.isRequired
}

export default AddToCartDialog
