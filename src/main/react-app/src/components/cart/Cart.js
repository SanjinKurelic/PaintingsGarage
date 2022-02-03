import {useCart} from 'react-use-cart'
import {Button, Col, ListGroup, Row} from 'react-bootstrap'
import {baseUrl, useCheckoutMutation} from '../../redux/api/baseApi'
import camera from '../../assets/camera.png'
import canvas from '../../assets/canvas.png'
import {AiOutlineCloseCircle} from 'react-icons/all'
import {useHistory} from 'react-router-dom'
import {useSelector} from 'react-redux'
import {selectCurrentUser} from '../../redux/slice/currentUserSlice'

const Cart = () => {
  const currentUser = useSelector(selectCurrentUser)
  const history = useHistory()
  const {
    isEmpty,
    items,
    updateItem,
    removeItem,
    cartTotal,
    emptyCart,
    totalUniqueItems
  } = useCart()
  const [checkout] = useCheckoutMutation()

  const doCheckout = () => {
    if (!currentUser || !currentUser.id) {
      history.push('/login')
    }
    const cartItems = items.map(item => ({photoId: item.id, photoType: item.pictrueType}))
    checkout(cartItems)
    emptyCart()
    history.push('/')
  }

  if (isEmpty) {
    return (<p className="alert alert-warning m-5">Your cart is empty</p>)
  }

  return (
    <div className="container-small">
      <div className="container-small-centered">
        <h3 className="m-5 text-center container-lg">Cart items</h3>
        <div className="m-5">
          <p className="d-inline-block p-2"><i>{totalUniqueItems} {totalUniqueItems > 1 ? 'items' : 'item'} in cart</i>
          </p>
          <Button className="button-dark float-end" onClick={() => emptyCart()}>Remove All</Button>
        </div>
        <hr/>
        {items.map((item) => (
          <Row className="m-3" key={item.id}>
            <Col className="col-2">
              <img src={`${baseUrl}/photo/${item.data.thumbnail}`} alt={item.data.description} width="100%"/>
            </Col>
            <Col>
              <p><b>{item.data.title}</b></p>
              <p className="text-small">{item.data.author}</p>
            </Col>
            <Col className="col-2">
              <ListGroup horizontal>
                <ListGroup.Item className="switcher-item text-center"
                                data-selected={item.pictureType === 'DIGITAL'}
                                onClick={() => updateItem(item.id, {
                                  pictureType: 'DIGITAL',
                                  price: item.data.digitalPrice
                                })}>
                  <img height="30px" alt="Digital" src={camera}/>
                </ListGroup.Item>
                <ListGroup.Item className="switcher-item text-center"
                                data-selected={item.pictureType === 'PHYSICAL'}
                                onClick={() => {
                                  console.log('update')
                                  updateItem(item.id, {
                                    pictureType: 'PHYSICAL',
                                    price: item.data.paintingPrice
                                  })
                                }}>
                  <img height="30px" alt="Physical" src={canvas}/>
                </ListGroup.Item>
              </ListGroup>
            </Col>
            <Col className="col-2 text-end">
              {item.price} €
            </Col>
            <Col className="col-1">
              <div className="cursor-pointer" onClick={() => removeItem(item.id)}>
                <AiOutlineCloseCircle color="red" size="2em"/>
              </div>
            </Col>
          </Row>
        ))}
        <hr/>
        <Row className="m-4">
          <Col className="col-2"/>
          <Col/>
          <Col className="col-2 text-end"><b>Total price:</b></Col>
          <Col className="col-2 text-end">{cartTotal.toLocaleString(undefined, {minimumFractionDigits: 2})} €</Col>
          <Col className="col-1"/>
        </Row>
        <Button className="button-dark float-end" onClick={() => doCheckout()}>Checkout</Button>
      </div>
    </div>
  )
}

export default Cart
