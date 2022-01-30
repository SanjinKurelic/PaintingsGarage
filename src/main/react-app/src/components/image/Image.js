import PropTypes from 'prop-types'
import moment from 'moment'
import ImageActionButton from './ImageActionButton'
import {AiOutlineCloseCircle} from 'react-icons/all'
import {photoUrl} from '../../redux/api/photoApi'
import './image.scss'
import HashtagList from '../hashtag/HashtagList'
import {useDispatch} from 'react-redux'
import {showDialog} from '../../redux/slice/currentDialogSlice'

const Image = ({image, setSelectedImage}) => {
  const closeDialog = () => {
    setSelectedImage(null)
  }

  const dispatch = useDispatch()
  const imageActionButtonClicked = (actionType) => {
    dispatch(showDialog({type: actionType, data: image}))
  }

  return (
    <div className="modal show fade d-block" onClick={closeDialog} onContextMenu={closeDialog}>
      <div className="modal-fullscreen">
        <div className="modal-content image-content">
          <div className="modal-body row p-5">
            <div className="col-9 image-content-image" style={{background: `url(${photoUrl}/download/${image.path})`}}/>
            <div className="col-3 p-4 position-relative">
              <div className="float-end" role="button">
                <AiOutlineCloseCircle size="2em"/>
              </div>
              <h2>{image.author}</h2>
              <div className="my-5">
                <p><b className="me-2">Uploaded:</b>{moment(image.uploaded).format('YYYY-MM-DD HH:mm')}</p>
                <p className="my-4">{image.description}</p>
                <p className="my-4">Digital price: {image.digitalPrice} €</p>
                <p className="my-4">Painting price: {image.paintingPrice} €</p>
              </div>
              <ImageActionButton type={image.ownershipType} size="2em" callback={imageActionButtonClicked}/>
              <div className="position-absolute image-content-tags"><HashtagList hashtagItems={image.hashtags}/></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

Image.propType = {
  setSelectedImage: PropTypes.func,
  image: PropTypes.shape({
    path: PropTypes.string.isRequired,
    author: PropTypes.string.isRequired,
    uploaded: PropTypes.string.isRequired,
    description: PropTypes.string,
    ownershipType: PropTypes.string,
    hashtags: PropTypes.array
  })
}

export default Image