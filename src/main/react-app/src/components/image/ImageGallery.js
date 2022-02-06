import PropTypes from 'prop-types'
import moment from 'moment'
import ImageActionButton from './ImageActionButton'
import HashtagList from '../hashtag/HashtagList'
import {baseUrl} from '../../redux/api/baseApi'
import {useDispatch} from 'react-redux'
import {showDialog} from '../../redux/slice/currentDialogSlice'
import {Col, Row} from 'react-bootstrap'

const ImageGallery = ({images, setSelectedImage}) => {
  const dispatch = useDispatch()
  const imageActionButtonClicked = (actionType, image) => {
    dispatch(showDialog({type: actionType, data: image}))
  }

  return (
    <Row>
      {images.map((image) => (
        <div key={image.path} className="col-lg-4 col-md-6 p-2">
          <Row className="justify-content-between p-2">
            <Col className="col-9"><b>{image.title}</b></Col>
            <Col className="col-3 text-end text-extra-small fade-to-right">
              <p className="p-0 m-0">{moment(image.uploaded).format('YYYY-MM-DD')}</p>
              <p className="p-0 m-0">{moment(image.uploaded).format('HH:mm')}</p>
            </Col>
          </Row>
          <img onClick={() => setSelectedImage(image)} width="100%" alt={image.description}
               src={`${baseUrl}/photo/${image.thumbnail}`} role="button"/>
          <Row className="justify-content-between p-2">
            <Col className="col-8 overflow-hidden"><HashtagList hashtagItems={image.hashtags}/></Col>
            <Col className="col-4 text-end fade-to-right">
              {image.ownershipType === 'NONE' && <span className="d-inline-block mx-2">{image.digitalPrice} €</span>}
              <ImageActionButton type={image.ownershipType}
                                 callback={(actionType) => imageActionButtonClicked(actionType, image)}/>
            </Col>
          </Row>
        </div>
      ))}
    </Row>
  )
}

ImageGallery.propTypes = {
  setSelectedImage: PropTypes.func.isRequired,
  images: PropTypes.arrayOf(PropTypes.shape({
    path: PropTypes.string.isRequired,
    thumbnail: PropTypes.string.isRequired,
    author: PropTypes.string.isRequired,
    uploaded: PropTypes.string.isRequired,
    description: PropTypes.string,
    ownershipType: PropTypes.string,
    hashtags: PropTypes.array
  }))
}

ImageGallery.defaultProps = {
  images: []
}

export default ImageGallery