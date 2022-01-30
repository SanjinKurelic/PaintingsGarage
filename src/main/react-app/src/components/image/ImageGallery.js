import PropTypes from 'prop-types'
import moment from 'moment'
import ImageActionButton from './ImageActionButton'
import HashtagList from '../hashtag/HashtagList'
import {photoUrl} from '../../redux/api/photoApi'
import './imageGallery.scss'
import {useDispatch} from 'react-redux'
import {showDialog} from '../../redux/slice/currentDialogSlice'

const ImageGallery = ({images, setSelectedImage}) => {
  const dispatch = useDispatch()
  const imageActionButtonClicked = (actionType, image) => {
    dispatch(showDialog({type: actionType, data: image}))
  }

  return (
    <div className="row">
      {images.map((image) => (
        <div key={image.path} className="col-lg-4 col-md-6 p-2">
          <div className="row justify-content-between p-2">
            <div className="col-5"><b>{image.author}</b></div>
            <div className="col-5 text-end image-description-time">
              <p className="p-0 m-0">{moment(image.uploaded).format('YYYY-MM-DD')}</p>
              <p className="p-0 m-0">{moment(image.uploaded).format('HH:mm')}</p>
            </div>
          </div>
          <img onClick={() => setSelectedImage(image)} width="100%" alt={image.description}
               src={`${photoUrl}/download/${image.thumbnail}`} role="button"/>
          <div className="row justify-content-between p-2">
            <div className="col-9 overflow-hidden"><HashtagList hashtagItems={image.hashtags}/></div>
            <div className="col-3 text-end image-description-hashtag-fade">
              {image.ownershipType === 'NONE' && <span className="d-inline-block mx-2">{image.digitalPrice} €</span>}
              <ImageActionButton type={image.ownershipType}
                                 callback={(actionType) => imageActionButtonClicked(actionType, image)}/>
            </div>
          </div>
        </div>
      ))}
    </div>
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