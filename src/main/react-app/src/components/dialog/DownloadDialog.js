import {
  Button,
  Col,
  Dropdown,
  DropdownButton,
  ModalBody,
  ModalDialog,
  ModalFooter,
  ModalTitle,
  Row
} from 'react-bootstrap'
import ModalHeader from 'react-bootstrap/ModalHeader'
import PropTypes from 'prop-types'
import {useDispatch} from 'react-redux'
import {hideDialog} from '../../redux/slice/currentDialogSlice'
import {useState} from 'react'

const DownloadDialog = ({image}) => {
  const dispatch = useDispatch()
  const [imageSize, setImageSize] = useState('NORMAL')

  const download = () => {
    console.log(image)
    console.log(imageSize)
    dispatch(hideDialog(null))
  }

  const cancel = () => {
    dispatch(hideDialog(null))
  }

  return (
    <div className="position-fixed top-0 bottom-0 w-100 dialog-box">
      <ModalDialog centered={true}>
        <ModalHeader>
          <ModalTitle>Download image</ModalTitle>
        </ModalHeader>
        <ModalBody>
          <Row>
            <Col>Image size:</Col>
            <Col>
              <DropdownButton title="Dropdown button">
                <Dropdown.Item onSelect={() => setImageSize('NORMAL')} active={true}>Normal</Dropdown.Item>
                <Dropdown.Item onSelect={() => setImageSize('SMALL')}>Small</Dropdown.Item>
              </DropdownButton>
            </Col>
          </Row>
          <Row>
            <Col>Filter:</Col>
            <Col>[] Sepia</Col>
          </Row>
        </ModalBody>
        <ModalFooter>
          <Button className="dialog-box-button" onClick={cancel}>Cancel</Button>
          <Button className="dialog-box-button" onClick={download}>Download</Button>
        </ModalFooter>
      </ModalDialog>
    </div>
  )
}

DownloadDialog.propTypes = {
  image: PropTypes.object.isRequired
}

export default DownloadDialog
