import PropTypes from 'prop-types'
import moment from 'moment'
import ImageActionButton from './ImageActionButton'
import {AiOutlineCloseCircle} from 'react-icons/all'
import {baseUrl, useAddPhotoMutation, useUpdatePhotoMutation} from '../../redux/api/baseApi'
import './image.scss'
import HashtagList from '../hashtag/HashtagList'
import {useDispatch} from 'react-redux'
import {showDialog} from '../../redux/slice/currentDialogSlice'
import {Button, Col, Form, Row} from 'react-bootstrap'
import {useState} from 'react'

const Image = ({image, closeCallback, editable, isNew}) => {
  const closeDialog = () => {
    closeCallback()
  }

  const dispatch = useDispatch()
  const imageActionButtonClicked = (actionType) => {
    dispatch(showDialog({type: actionType, data: image}))
    closeDialog()
  }

  // Editable Image component
  const [validated, setValidate] = useState(false)
  const [inputs, setInputs] = useState(image)

  const changeInput = (e) => {
    setInputs(values => ({...values, [e.target.name]: e.target.value}))
  }

  // API calls
  const [updatePhoto] = useUpdatePhotoMutation()
  const [addPhoto] = useAddPhotoMutation()
  const submit = (event) => {
    const form = event.currentTarget

    if (form.checkValidity() !== false) {
      if (isNew) {
        addPhoto(new FormData(form))
      } else {
        updatePhoto({
          id: image.id,
          title: inputs.title,
          description: inputs.description,
          digitalPrice: inputs.digitalPrice,
          paintingPrice: inputs.paintingPrice
        })
      }

      closeCallback()
    }

    setValidate(true)
    event.preventDefault()
    event.stopPropagation()
  }

  return (
    <div className="modal show fade d-block" onContextMenu={closeDialog}>
      <div className="modal-fullscreen">
        <div className="modal-content image-content">
          <Form className="h-100" noValidate validated={validated} onSubmit={submit}>
            <Row className="modal-body h-100 p-5">
              {!isNew &&
                <Col className="col-9 image-content-image"
                     style={{background: `url(${baseUrl}/photo/${image.path})`}}/>}
              {isNew && <Col className="col-9 position-relative">
                <input className="position-absolute top-50 start-50" required={isNew} type="file" name="file"
                       onChange={changeInput}/>
              </Col>}
              <Col className="p-4 position-relative">
                <div className="float-end" role="button" onClick={closeDialog}>
                  <AiOutlineCloseCircle size="2em"/>
                </div>
                <h2>{image.title}</h2>
                <div className="my-5">
                  <p><b className="me-2">Author:</b>{image.author}</p>
                  <p><b className="me-2">Uploaded:</b>{moment(image.uploaded).format('YYYY-MM-DD HH:mm')}</p>
                  {editable && <>
                    <Form.Group as={Row} className="my-4">
                      <Form.Label column className="col-4"><b>Change title:</b></Form.Label>
                      <Col>
                        <Form.Control required={true} type="text" name="title" onChange={changeInput}
                                      value={inputs.title}/>
                      </Col>
                    </Form.Group>
                    <Form.Group as={Row} className="my-4">
                      <Col>
                      <textarea className="w-100" required={true} name="description" placeholder="Description"
                                onChange={changeInput} value={inputs.description} rows="4"/>
                      </Col>
                    </Form.Group>
                    <Form.Group as={Row} className="my-4">
                      <Form.Label column className="col-5"><b>Digital price (€):</b></Form.Label>
                      <Col>
                        <Form.Control required={true} type="text" name="digitalPrice" onChange={changeInput}
                                      value={(inputs.digitalPrice || 0).toLocaleString(undefined, {minimumFractionDigits: 2})}/>
                      </Col>
                    </Form.Group>
                    <Form.Group as={Row} className="my-4">
                      <Form.Label column className="col-5"><b>Painting price (€):</b></Form.Label>
                      <Col>
                        <Form.Control required={true} type="text" name="paintingPrice" onChange={changeInput}
                                      value={(inputs.paintingPrice || 0).toLocaleString(undefined, {minimumFractionDigits: 2})}/>
                      </Col>
                    </Form.Group>
                    <Button className="button-light float-end" variant="primary" type="submit">Save</Button>
                  </>}
                  {!editable && <>
                    <p className="my-4">{image.description}</p>
                    <p className="my-4">Digital
                      price: {image.digitalPrice.toLocaleString(undefined, {minimumFractionDigits: 2})} €</p>
                    <p className="my-4">Painting
                      price: {image.paintingPrice.toLocaleString(undefined, {minimumFractionDigits: 2})} €</p>
                  </>}
                </div>
                {!editable &&
                  <ImageActionButton type={image.ownershipType} size="2em" callback={imageActionButtonClicked}/>}
                <div className="position-absolute image-content-tags"><HashtagList hashtagItems={image.hashtags}/></div>
              </Col>
            </Row>
          </Form>
        </div>
      </div>
    </div>
  )
}

Image.propTypes = {
  closeCallback: PropTypes.func.isRequired,
  editable: PropTypes.bool,
  isNew: PropTypes.bool,
  image: PropTypes.shape({
    path: PropTypes.string,
    author: PropTypes.string.isRequired,
    uploaded: PropTypes.string.isRequired,
    description: PropTypes.string,
    ownershipType: PropTypes.string,
    hashtags: PropTypes.array
  })
}

Image.defaultProps = {
  editable: false,
  isNew: false
}

export default Image