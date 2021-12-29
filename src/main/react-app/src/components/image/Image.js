import PropTypes from 'prop-types'
import moment from 'moment'
import ImageActionButton from './ImageActionButton'
import {AiOutlineCloseCircle} from 'react-icons/all'
import {photoUrl} from '../../redux/api/photoApi'

const Image = ({image, setSelectedImage}) => {
  const closeDialog = () => {
    setSelectedImage(null)
  }

  return (
    <div className="modal show fade" style={{display: 'block'}} onClick={closeDialog} onContextMenu={closeDialog}>
      <div className="modal-fullscreen">
        <div className="modal-content" style={{background: 'black', color: '#BEBEBE'}}>
          <div className="modal-body row p-5">
            <div className="col-9" style={{
              background: `url(${photoUrl}/download/${image.path})`,
              backgroundSize: 'contain',
              backgroundRepeat: 'no-repeat',
              backgroundPosition: 'center',
              height: '100%'
            }}/>
            <div className="col-3 p-4">
              <div className="text-end" role="button">
                <AiOutlineCloseCircle size="2em"/>
              </div>
              <h2>{image.author}</h2>
              <div className="my-5">
                <p><b className="me-2">Uploaded:</b>{moment(image.uploaded).format('YYYY-MM-DD hh:mm')}</p>
                <p className="my-4">{image.description}</p>
              </div>
              <ImageActionButton type={image.ownershipType} size="2em"/>
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